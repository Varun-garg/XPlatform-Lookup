from .serializer import AdminSerializer, StudentSerializer, HostelSerializer, MarksStatusSerializer, MarksSubjectsSerializer
from rest_framework import generics
from rest_framework.views import APIView
from rest_framework.response import Response
from .models import studentdb, hostel_info, marks_status, marks_subjects, SubmitReview
import json
from django.shortcuts import HttpResponse
from rest_framework.decorators import api_view
from django.views.decorators.csrf import csrf_exempt
from django.core.validators import validate_email
from django import forms
import re
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


@api_view(['POST'])
def addstudent(request):
    response_data = {}
    errors = []
    var = request.session.get('group_name')
    if (var is not None) and (request.session['group_name'] == 'STAD' or request.session['group_name'] == 'OAD'):
        if request.method == 'POST':
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
                errors.append("program_name: Enter program_name")
            school = request.POST.get('school')
            if (school is None) or (len(school)==0):
                errors.append("school: Enter School Name")
            roll_no = request.POST.get('roll_no')
            if (roll_no is None) or (len(roll_no)==0):
                errors.append("roll_no: Enter Roll Number")
            elif re.match('[0-9]{1,}[A-Z]{1,}[0-9]{1,}',roll_no) is None:
                errors.append("roll_no: Invalid Roll Number")
            existing_entry = studentdb.objects.filter(roll_no = roll_no)
            if existing_entry.count() > 0:
                errors.append("roll_no: Entry corresponding to this roll number already exists")
            father_name = request.POST.get('father_name', '')
            mother_name = request.POST.get('mother_name', '')
            dob = request.POST.get('dob')
            if (dob is None) or (len(dob)==0):
                errors.append("dob: Enter Date Of Birth")
            sex = request.POST.get('sex', '')
            email = request.POST.get('email')
            if (email is None) or (len(email)==0):
                errors.append("email: Enter email id")
            else :
                try:
                    validate_email(email)
                except forms.ValidationError:
                    errors.append("email: Invalid email id")
            phone = request.POST.get('phone')
            if (phone is None) or (len(phone)==0):
                errors.append("phone: Enter phone Number")
            elif not phone.isdigit():
                errors.append("phone: Invalid phone Number")
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
            else:
                response_data['errors'] = errors
                response_data['message'] = 'fail'
        else:
            response_data['message'] = 'fail'
    else:
        errors.append("Permission: You don't have permissions to create a new user entry.")
        response_data['errors'] = errors
        response_data['message'] = "fail"
    return HttpResponse(json.dumps(response_data), content_type="application/json")


@api_view(['POST'])
def addhostelinfo(request):
    response_data = {}
    errors = []
    var = request.session.get('group_name')
    if (var is not None) and (request.session['group_name'] == 'HAD' or request.session['group_name'] == 'OAD'):
        if request.method == 'POST':
            roll_num = request.POST.get('roll_no')
            if (roll_num is None) or (len(roll_num)==0):
                errors.append("roll_no: Enter Roll Number")
            elif re.match('[0-9]{1,}[A-Z]{1,}[0-9]{1,}',roll_num) is None:
                errors.append("roll_no: Invalid Roll Number")
            existing_entry = studentdb.objects.filter(roll_no = roll_num)
            if existing_entry.count() is 0:
                errors.append("roll_no: No student with this roll number exists")
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
            else:
                response_data['errors'] = errors
                response_data['message'] = 'fail'
        else:
            response_data['message'] = 'fail'
    else:
        response_data['message'] = "fail."
        errors.append("Permission: You don't have permissions to create a new hostel entry.")
        response_data['errors'] = errors
    return HttpResponse(json.dumps(response_data), content_type="application/json")


@api_view(['POST'])
def addmarksinfo(request):
    response_data = {}
    errors = []
    var = request.session.get('group_name')
    if (var is not None) and (request.session['group_name'] == 'MAD' or request.session['group_name'] == 'OAD'):
        if request.method == 'POST':
            roll_num = request.POST.get('roll_no')
            if (roll_num is None) or (len(roll_num)==0):
                errors.append("roll_no: Enter Roll Number")
            elif re.match('[0-9]{1,}[A-Z]{1,}[0-9]{1,}',roll_num) is None:
                errors.append("roll_no: Invalid Roll Number")
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
def add_review(request):
    response_data = {}
    errors = []
    var = request.session.get('group_name')
    if var is not None:
        if request.method == 'POST':
            stu_roll = request.POST.get('stu_roll')
            if (stu_roll is None) or (len(stu_roll) == 0):
                errors.append("stu_roll: Enter Student Roll Number")
            elif re.match('[0-9]{1,}[A-Z]{1,}[0-9]{1,}',stu_roll) is None:
                errors.append("stu_roll: Invalid Student Roll Number")
            comment = request.POST.get('comment')
            if (comment is None) or (len(comment)==0):
                errors.append("comment: Enter Comment")
            user = request.session.get('user')
            if len(errors) == 0:
                new_review = SubmitReview()
                new_review.student = stu_roll
                new_review.comment = comment
                new_review.user = user
                new_review.save()
                response_data['message'] = 'success'
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



