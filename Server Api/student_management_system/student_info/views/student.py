import csv
import datetime
import json
import re
from django import forms
from django.core.validators import validate_email
from django.db.models.query_utils import Q
from django.shortcuts import HttpResponse
from django.views.decorators.csrf import csrf_exempt
from rest_framework import generics
from rest_framework.decorators import api_view
from rest_framework.response import Response
from rest_framework.views import APIView
from student_info.models import studentdb
from student_info.serializer import AdminSerializer, StudentSerializer
from user_account.views import generateLogs


class AdminStudent_List(generics.ListAPIView):
    queryset = studentdb.objects.all()
    serializer_class = AdminSerializer


class AdminStudent_Detail(generics.RetrieveAPIView):
    queryset = studentdb.objects.all()
    lookup_field = 'roll_no'
    serializer_class = AdminSerializer


class Student_Detail(generics.RetrieveAPIView):
    lookup_field = 'roll_no'
    queryset = studentdb.objects.all()
    serializer_class = StudentSerializer


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