from .serializer import AdminSerializer, StudentSerializer, HostelSerializer, MarksStatusSerializer, MarksSubjectsSerializer
from rest_framework import generics
from rest_framework.views import APIView
from rest_framework.response import Response
from .models import studentdb, hostel_info, marks_status, marks_subjects
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



