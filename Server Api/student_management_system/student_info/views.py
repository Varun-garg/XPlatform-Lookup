from django.shortcuts import render
from .serializer import AdminSerializer, StudentSerializer
from rest_framework import generics
from .models import studentdb
import json
from django.shortcuts import HttpResponse

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


def addstudent(request):
    if request.method == 'GET':
        full_name = request.GET.get('full_name', '')
        enroll_no = request.GET.get('enroll_no')
        program_name = request.GET.get('program_name', '')
        school = request.GET.get('school', '')
        roll_no = request.GET.get('roll_no', '')
        father_name = request.GET.get('father_name', '')
        mother_name = request.GET.get('mother_name', '')
        dob = request.GET.get('dob')
        sex = request.GET.get('sex', '')
        email = request.GET.get('email', '')
        phone = request.GET.get('phone')

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

        response_data = {}

        response_data['message'] = 'success'
        return HttpResponse(json.dumps(response_data), content_type="application/json")


