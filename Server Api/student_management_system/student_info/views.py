from .serializer import AdminSerializer, StudentSerializer, HostelSerializer, MarksStatusSerializer, MarksSubjectsSerializer, SubmitReviewSerializer
from rest_framework import generics
from rest_framework.views import APIView
from rest_framework.response import Response
from .models import studentdb, hostel_info, marks_status, marks_subjects, SubmitReview
import json, re, datetime
from django.shortcuts import HttpResponse
from rest_framework.decorators import api_view
from django.views.decorators.csrf import csrf_exempt
from django.core.validators import validate_email
from django import forms
from django.db.models.query_utils import Q
from user_account.views import generateLogs
import csv

# These will be used in the admin section


class AdminStudent_List(generics.ListAPIView):
    queryset = studentdb.objects.all()
    serializer_class = AdminSerializer


class AdminStudent_Detail(generics.RetrieveAPIView):
    queryset = studentdb.objects.all()
    lookup_field = 'roll_no'
    serializer_class = AdminSerializer


# These will be used in the student section


class Student_Detail(generics.RetrieveAPIView):
    lookup_field = 'roll_no'
    queryset = studentdb.objects.all()
    serializer_class = StudentSerializer


class Hostel_Detail(generics.RetrieveAPIView):
    queryset = hostel_info.objects.all()
    lookup_field = 'roll_num'
    serializer_class = HostelSerializer


class Student_Exams(APIView):

    def get(self, request, **kwargs):
        subjects = marks_subjects.objects.filter(roll_num=kwargs['roll_num'], semester=kwargs['semester'])
        status = marks_status.objects.filter(roll_num=kwargs['roll_num'], semester=kwargs['semester'])
        subjects_serializer = MarksSubjectsSerializer(subjects, many=True)
        status_serializer = MarksStatusSerializer(status, many=True)
        return Response({'Marks Summary': subjects_serializer.data, 'Overall Result': status_serializer.data,})


class Review(generics.ListAPIView):
    serializer_class = SubmitReviewSerializer

    def get_queryset(self):
        var = self.request.session.get('permissions')
        sec1 = ''
        sec2 = ''
        sec3 = ''
        if var[5]=='1':
            sec1 = 'personal'
        if var[6]=='1':
            sec2 = 'hostel'
        if var[7] == '1':
            sec3 = 'exam'
        return SubmitReview.objects.filter(Q(section=sec1) | Q(section=sec2)|Q(section=sec3))



#student centric reviews
"""
class Review(APIView):
    def get(self, request, **kwargs):
        reviews = SubmitReview.objects.filter(student = kwargs['student'])
        review_result = SubmitReviewSerializer(reviews, many=True)
        return Response({'Review': review_result.data,})
"""

@api_view(['GET'])
def studentSearch(request):
    response_data = {}
    search = []
    query = request.GET.get('query')
    if (query is not None) and (len(query) > 0):
        output = studentdb.objects.filter(Q(full_name__icontains=query) | Q(enroll_no__icontains=query) | Q(program_name__icontains=query) | Q(school__icontains=query) | Q(roll_no__icontains=query) | Q(dob__icontains=query) | Q(email__icontains=query) | Q(phone__icontains=query) )
        for n in output:
            result = {'full_name': n.full_name, 'roll_no' : n.roll_no}
            search.append(result)
        response_data['search'] = search
    return HttpResponse(json.dumps(response_data), content_type="application/json")


@api_view(['POST'])
def addstudent(request):
    response_data = {}
    errors = []
    var = request.session.get('permissions')
    if (var is not None) and (var[5] == '1'):
        if request.method == 'POST':
            full_name = request.POST.get('full_name')
            if (full_name is None) or (len(full_name)==0):
                errors.append("full_name: Enter Full Name")
            elif full_name.isdigit():
                errors.append("full_name: Invalid Full Name")
            enroll_no = request.POST.get('enroll_no')
            if (enroll_no is None) or (len(enroll_no)==0):
                errors.append("enroll_no: Enter Enrollment Number")
            elif not enroll_no.isdigit():
                errors.append("enroll_no: Enter numeric values only.")
            program_name = request.POST.get('program_name')
            if (program_name is None) or (len(program_name)==0):
                errors.append("program_name: Enter Program Name")
            school = request.POST.get('school')
            if (school is None) or (len(school)==0):
                errors.append("school: Enter School Name")
            roll_no = request.POST.get('roll_no')
            if (roll_no is None) or (len(roll_no)==0):
                errors.append("roll_no: Enter Roll Number")
            elif re.match('[0-9]{1,}[A-Z]{1,}[0-9]{1,}',roll_no) is None:
                errors.append("roll_no: Wrong format of Roll Number, expecting like 13ICS029")
            else:
                existing_entry = studentdb.objects.filter(roll_no = roll_no)
                if existing_entry.count() > 0:
                    errors.append("roll_no: Entry corresponding to this roll number already exists")
            father_name = request.POST.get('father_name', '')
            mother_name = request.POST.get('mother_name', '')
            dob = request.POST.get('dob')
            if (dob is None) or (len(dob)==0):
                errors.append("dob: Enter Date Of Birth")
            else:
                try:
                    datetime.datetime.strptime(dob, '%Y-%m-%d')
                except ValueError:
                    errors.append("dob: Wrong format of Date Of Birth, expecting YYYY-MM-DD")
            sex = request.POST.get('sex', '')
            email = request.POST.get('email')
            if (email is None) or (len(email)==0):
                errors.append("email: Enter Email Id")
            else:
                try:
                    validate_email(email)
                except forms.ValidationError:
                    errors.append("email: Invalid Email Id")
            phone = request.POST.get('phone')
            if (phone is None) or (len(phone)==0):
                errors.append("phone: Enter Phone Number")
            elif not phone.isdigit():
                errors.append("phone: Invalid Phone Number")
            if len(errors) == 0:
                new_student = studentdb()
                new_student.full_name = full_name
                new_student.enroll_no = enroll_no
                new_student.program_name = program_name
                new_student.school = school
                new_student.roll_no = roll_no
                new_student.father_name = father_name
                new_student.mother_name = mother_name
                new_student.dob = dob
                new_student.sex = sex
                new_student.email = email
                new_student.phone = phone
                new_student.save()
                response_data['message'] = 'success'
                action = "Added Student"
                generateLogs(request, action)
            else:
                response_data['errors'] = errors
                response_data['message'] = 'fail'
        else:
            response_data['message'] = 'fail'
    else:
        errors.append("Permission: You don't have permissions to create a new student entry.")
        response_data['errors'] = errors
        response_data['message'] = "fail"
    return HttpResponse(json.dumps(response_data), content_type="application/json")


@api_view(['POST'])
def updateStudent(request):
    response_data = {}
    errors = []
    var = request.session.get('permissions')
    if (var is not None) and (var[5] == '1'):
        if request.method == 'POST':
            roll_no = request.POST.get('roll_no')
            if (roll_no is None) or (len(roll_no)==0):
                errors.append("roll_no: Enter Roll Number")
            elif re.match('[0-9]{1,}[A-Z]{1,}[0-9]{1,}',roll_no) is None:
                errors.append("roll_no: Wrong format of Roll Number, expecting like 13ICS029")
            else:
                try:
                    existing_entry = studentdb.objects.get(roll_no = roll_no)
                except studentdb.DoesNotExist:
                    existing_entry=None
                if existing_entry is None:
                    errors.append("roll_no: Entry corresponding to this roll number does not exists")
                else:
                    full_name = request.POST.get('full_name')
                    if (full_name is None) or (len(full_name)==0):
                        errors.append("full_name: Enter Full Name")
                    elif full_name.isdigit():
                        errors.append("full_name: Invalid Full Name")
                    enroll_no = request.POST.get('enroll_no')
                    if (enroll_no is None) or (len(enroll_no)==0):
                        errors.append("enroll_no: Enter Enrollment Number")
                    program_name = request.POST.get('program_name')
                    if (program_name is None) or (len(program_name)==0):
                        errors.append("program_name: Enter Program Name")
                    school = request.POST.get('school')
                    if (school is None) or (len(school)==0):
                        errors.append("school: Enter School Name")
                    father_name = request.POST.get('father_name', '')
                    mother_name = request.POST.get('mother_name', '')
                    dob = request.POST.get('dob')
                    if (dob is None) or (len(dob)==0):
                        errors.append("dob: Enter Date Of Birth")
                    else:
                        try:
                            datetime.datetime.strptime(dob, '%Y-%m-%d')
                        except ValueError:
                            errors.append("dob: Wrong format of Date Of Birth, expecting YYYY-MM-DD")
                    sex = request.POST.get('sex', '')
                    email = request.POST.get('email')
                    if (email is None) or (len(email)==0):
                        errors.append("email: Enter Email Id")
                    else:
                        try:
                            validate_email(email)
                        except forms.ValidationError:
                            errors.append("email: Invalid Email Id")
                    phone = request.POST.get('phone')
                    if (phone is None) or (len(phone)==0):
                        errors.append("phone: Enter Phone Number")
                    elif not phone.isdigit():
                        errors.append("phone: Invalid Phone Number")
                    if len(errors) == 0:
                        existing_entry.full_name = full_name
                        existing_entry.enroll_no = enroll_no
                        existing_entry.program_name = program_name
                        existing_entry.school = school
                        existing_entry.roll_no = roll_no
                        existing_entry.father_name = father_name
                        existing_entry.mother_name = mother_name
                        existing_entry.dob = dob
                        existing_entry.sex = sex
                        existing_entry.email = email
                        existing_entry.phone = phone
                        existing_entry.save()
                        response_data['message'] = 'success'
                        action = "Updated Student"
                        generateLogs(request, action)
                    else:
                        response_data['errors'] = errors
                        response_data['message'] = 'fail'
                response_data['errors'] = errors
        else:
            response_data['message'] = 'fail'
    else:
        errors.append("Permission: You don't have permissions to update a student entry.")
        response_data['errors'] = errors
        response_data['message'] = "fail"
    return HttpResponse(json.dumps(response_data), content_type="application/json")


@api_view(['POST'])
def deleteStudent(request):
    response_data = {}
    errors = []
    var = request.session.get('permissions')
    if (var is not None) and (var[5]=='1'):
        if request.method == 'POST':
            roll_no = request.POST.get('roll_no')
            if (roll_no is None) or (len(roll_no)==0):
                errors.append("roll_no: Enter Roll Number")
                response_data['message'] = "fail"
                response_data['errors'] = errors
            else:
                existing_entry = studentdb.objects.get(roll_no = roll_no)
                existing_entry.delete()
                response_data['message'] = "success"
                action = "Deleted Student"
                generateLogs(request, action)
        else:
            response_data['message'] = 'fail'
    else:
        errors.append("Permission: You don't have permissions to delete a student entry.")
        response_data['message'] = 'fail'
        response_data['errors'] = errors
    return HttpResponse(json.dumps(response_data), content_type="application/json")


@api_view(['POST'])
def addhostelinfo(request):
    response_data = {}
    errors = []
    var = request.session.get('permissions')
    if (var is not None) and (var[6]=='1'):
        if request.method == 'POST':
            roll_num = request.POST.get('roll_no')
            if (roll_num is None) or (len(roll_num)==0):
                errors.append("roll_no: Enter Roll Number")
            elif re.match('[0-9]{1,}[A-Z]{1,}[0-9]{1,}',roll_num) is None:
                errors.append("roll_no: Invalid Roll Number")
            else:
                existing_entry = studentdb.objects.filter(roll_no = roll_num)
                if existing_entry.count() is 0:
                    errors.append("roll_no: No student with this roll number exists")
                else:
                    existing_hostel = hostel_info.objects.filter(roll_num = roll_num)
                    if existing_hostel.count() > 0:
                        errors.append("roll_no: Student already exists.")

            hostel_name = request.POST.get('hostel_name')
            if (hostel_name is None) or (len(hostel_name)==0):
                errors.append("hostel_name: Enter Hostel Name")
            room_num = request.POST.get('room_num')
            if (room_num is None) or (len(room_num)==0):
                errors.append("room_num: Enter Room Number")
            warden_name = request.POST.get('warden_name')
            warden_mob = request.POST.get('warden_mob')
            caretaker_name = request.POST.get('caretaker_name')
            caretaker_num = request.POST.get('caretaker_num')
            if len(errors) == 0:
                new_hostel_info = hostel_info()
                new_hostel_info.roll_num = roll_num
                new_hostel_info.hostel_name = hostel_name
                new_hostel_info.room_num = room_num
                new_hostel_info.warden_name = warden_name
                new_hostel_info.warden_mob = warden_mob
                new_hostel_info.caretaker_name = caretaker_name
                new_hostel_info.caretaker_num = caretaker_num
                new_hostel_info.save()
                response_data['message'] = 'success'
                action = "Added Hostel Info"
                generateLogs(request, action)
            else:
                response_data['errors'] = errors
                response_data['message'] = 'fail'
        else:
            response_data['message'] = 'fail'
    else:
        response_data['message'] = "fail"
        errors.append("Permission: You don't have permissions to create a new hostel entry.")
        response_data['errors'] = errors
    return HttpResponse(json.dumps(response_data), content_type="application/json")


@api_view(['POST'])
def updateStudentHostel(request):
    response_data = {}
    errors = []
    var = request.session.get('permissions')
    if (var is not None) and (var[6]=='1'):
        if request.method == 'POST':
            roll_no = request.POST.get('roll_no')
            if (roll_no is None) or (len(roll_no)==0):
                errors.append("roll_no: Enter Roll Number")
            elif re.match('[0-9]{1,}[A-Z]{1,}[0-9]{1,}',roll_no) is None:
                errors.append("roll_no: Wrong format of Roll Number, expecting like 13ICS029")
            else:
                try:
                    existing_entry = hostel_info.objects.get(roll_num = roll_no)
                except hostel_info.DoesNotExist:
                    existing_entry=None
                if existing_entry is None:
                    errors.append("roll_no: Entry corresponding to this roll number does not exists")
                else:
                    hostel_name = request.POST.get('hostel_name')
                    if (hostel_name is None) or (len(hostel_name)==0):
                        errors.append("hostel_name: Enter Hostel Name")
                    room_num = request.POST.get('room_num')
                    if (room_num is None) or (len(room_num)==0):
                        errors.append("room_num: Enter Room Number")
                    warden_name = request.POST.get('warden_name')
                    warden_mob = request.POST.get('warden_mob')
                    caretaker_name = request.POST.get('caretaker_name')
                    caretaker_num = request.POST.get('caretaker_num')
                    if len(errors) == 0:
                        existing_entry.hostel_name = hostel_name
                        existing_entry.room_num = room_num
                        existing_entry.warden_name = warden_name
                        existing_entry.warden_mob = warden_mob
                        existing_entry.caretaker_name = caretaker_name
                        existing_entry.caretaker_num = caretaker_num
                        existing_entry.save()
                        response_data['message'] = 'success'
                        action = "Updated Hostel Info"
                        generateLogs(request, action)
                    else:
                        response_data['errors'] = errors
                        response_data['message'] = 'fail'
                response_data['errors'] = errors
        else:
            response_data['message'] = 'fail'
    else:
        errors.append("Permission: You don't have permissions to update a hostel entry.")
        response_data['errors'] = errors
        response_data['message'] = "fail"
    return HttpResponse(json.dumps(response_data), content_type="application/json")


@api_view(['POST'])
def deleteHostelStudent(request):
    response_data = {}
    errors = []
    var = request.session.get('permissions')
    if (var is not None) and (var[6]=='1'):
        if request.method == 'POST':
            roll_no = request.POST.get('roll_no')
            if (roll_no is None) or (len(roll_no)==0):
                errors.append("roll_no: Enter Roll Number")
                response_data['message'] = "fail"
                response_data['errors'] = errors
            else:
                existing_entry = hostel_info.objects.get(roll_num = roll_no)
                existing_entry.delete()
                response_data['message'] = "success"
                action = "Deleted Hostel Info"
                generateLogs(request, action)
        else:
            response_data['message'] = 'fail'
    else:
        errors.append("Permission: You don't have permissions to delete a hostel entry.")
        response_data['message'] = 'fail'
        response_data['errors'] = errors
    return HttpResponse(json.dumps(response_data), content_type="application/json")


@api_view(['GET'])
def returnSemesters(request, **kwargs):
    response_data = {}
    sems = []
    roll_no = kwargs['roll_num']
    existing_semesters = marks_status.objects.filter(roll_num=roll_no)
    for i in existing_semesters:
        sems.append(i.semester)
    sems = list(set(sems))
    response_data['semesters'] = sems
    response_data['message'] = "success"
    return HttpResponse(json.dumps(response_data), content_type="application.json")


@api_view(['POST'])
def addmarksinfo(request):
    response_data = {}
    errors = []
    var = request.session.get('permissions')
    if (var is not None) and (var[7]=='1'):
        if request.method == 'POST':
            roll_num = request.POST.get('roll_no')
            if (roll_num is None) or (len(roll_num)==0):
                errors.append("roll_no: Enter Roll Number")
            elif re.match('[0-9]{1,}[A-Z]{1,}[0-9]{1,}',roll_num) is None:
                errors.append("roll_no: Invalid Roll Number")
            else:
                existing_entry = studentdb.objects.filter(roll_no = roll_num)
                if existing_entry.count() is 0:
                    errors.append("roll_no: No student with this roll number exists")
            subject_code = request.POST.get('subject_code')
            if (subject_code is None) or (len(subject_code)==0):
                errors.append("subject_code: Enter Subject Code")
            grade = request.POST.get('grade')
            if (grade is None) or (len(grade)==0):
                errors.append("grade: Enter Grade")
            tc = request.POST.get('tc')
            if (tc is None) or (len(tc)==0):
                errors.append("tc: Enter Total Credits")
            tgp = request.POST.get('tgp')
            if (tgp is None) or (len(tgp)==0):
                errors.append("tgp: Enter Total Grade Point")
            sgpa = request.POST.get('sgpa')
            if (sgpa is None) or (len(sgpa)==0):
                errors.append("sgpa: Enter SGPA")
            result = request.POST.get('result')
            if (result is None) or (len(result)==0):
                errors.append("result: Enter Status (Result)")
            semester = request.POST.get('semester')
            if (semester is None) or (len(semester)==0):
                errors.append("semester: Enter Semester")
            elif not semester.isdigit():
                errors.append("semester: Invalid Semester")
            if len(errors) == 0:
                new_marks_subjects = marks_subjects()
                new_marks_subjects.roll_num = roll_num
                new_marks_subjects.subject_code = subject_code
                new_marks_subjects.grade = grade
                new_marks_subjects.semester = semester
                new_marks_status = marks_status()
                new_marks_status.roll_num = roll_num
                new_marks_status.tc = tc
                new_marks_status.tgp = tgp
                new_marks_status.sgpa = sgpa
                new_marks_status.result = result
                new_marks_status.semester = semester
                new_marks_status.save()
                new_marks_subjects.save()
                response_data['message'] = 'success'
                action = "Added Exams Info"
                generateLogs(request, action)
            else:
                response_data['errors'] = errors
                response_data['message'] = 'fail'
        else:
            response_data['message'] = 'fail'
    else:
        response_data['message'] = "fail"
        errors.append("Permission: You don't have permissions to create/update exam entry.")
        response_data['errors'] = errors
    return HttpResponse(json.dumps(response_data), content_type="application/json")


@api_view(['POST'])
def deleteExamStudent(request):
    response_data = {}
    errors = []
    var = request.session.get('permissions')
    if (var is not None) and (var[7] == '1'):
        if request.method == 'POST':
            roll_no = request.POST.get('roll_no')
            semester = request.POST.get('semester')

            if (roll_no is None) or (len(roll_no)==0):
                errors.append("roll_no: Enter Roll Number")
                response_data['message'] = "fail"
                response_data['errors'] = errors

            elif (semester is None) or (len(semester)==0):
                errors.append("semester: Enter Semester")
                response_data['message'] = "fail"
                response_data['errors'] = errors
            else:
                try:
                    existing_entry_marks = marks_status.objects.get(roll_num = roll_no, semester=semester)
                except marks_status.DoesNotExist:
                    existing_entry_marks=None
                try:
                    existing_entry_subjects = marks_subjects.objects.filter(roll_num = roll_no, semester = semester)
                except marks_subjects.DoesNotExist:
                    existing_entry_subjects=None
                if existing_entry_marks is None or existing_entry_subjects is None:
                    errors.append("roll_no: Details corresponding to this roll_no and semester does not exist.")
                    response_data['errors'] = errors
                    response_data['message'] = "fail"
                else:
                    existing_entry_marks.delete()
                    existing_entry_subjects.delete()
                    response_data['message'] = "success"
                    action = "Deleted Exam Info"
                    generateLogs(request, action)
                    response_data['message'] = "success"
        else:
            response_data['message'] = 'fail'
    else:
        errors.append("Permission: You don't have permissions to delete an exam entry.")
        response_data['message'] = 'fail'
        response_data['errors'] = errors
    return HttpResponse(json.dumps(response_data), content_type="application/json")


@api_view(['POST'])
def add_review(request):
    response_data = {}
    errors = []
    var = request.session.get('permissions')
    if (var is not None) and (var[8]=='1'):
        if request.method == 'POST':
            stu_roll = request.POST.get('stu_roll')
            if (stu_roll is None) or (len(stu_roll) == 0):
                errors.append("stu_roll: Enter Student Roll Number")
            elif re.match('[0-9]{1,}[A-Z]{1,}[0-9]{1,}',stu_roll) is None:
                errors.append("stu_roll: Invalid Student Roll Number")
            else:
                existing_entry = studentdb.objects.filter(roll_no = stu_roll)
                if existing_entry.count() is 0:
                    errors.append("stu_roll: No student with this roll number exists")
            section = request.POST.get('section')
            if (section is None) or (len(section)==0):
                errors.append("section: Enter Section")
            elif (section != 'personal') and (section != 'hostel') and (section != 'exam'):
                errors.append("section: No such Section")
            comment = request.POST.get('comment')
            if (comment is None) or (len(comment)==0):
                errors.append("comment: Enter Comment")
            user = request.session.get('user')
            if len(errors) == 0:
                new_review = SubmitReview()
                new_review.student = stu_roll
                new_review.section = section
                new_review.comment = comment
                new_review.user = user
                new_review.save()
                response_data['message'] = 'success'
                action = "Added Review"
                generateLogs(request, action)
            else:
                response_data['errors'] = errors
                response_data['message'] = 'fail'
        else:
            response_data['message'] = 'fail'
    else:
        response_data['message'] = "fail"
        errors.append("Permission: You don't have permissions to create a review.")
        response_data['errors'] = errors
    return HttpResponse(json.dumps(response_data), content_type="application/json")


@api_view(['POST'])
def upload_excel(request):
    stuList = request.FILES['stuList']
    response_data = {}
    reader = csv.reader(stuList)
    for row in reader:
        _, created = studentdb.objects.get_or_create(
            full_name = row[0],
            enroll_no = row[1],
            program_name = row[2],
            school = row[3],
            roll_no = row[4],
            father_name = row[5],
            mother_name = row[6],
            dob = row[7],
            sex = row[8],
            email = row[9],
            phone = row[10],
       )
    response_data["message"] = "success"
    return HttpResponse(json.dumps(response_data), content_type="application/json")