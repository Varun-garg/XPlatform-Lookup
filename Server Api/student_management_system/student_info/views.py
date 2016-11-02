from .serializer import AdminSerializer, StudentSerializer, HostelSerializer, MarksStatusSerializer, MarksSubjectsSerializer
from rest_framework import generics
from rest_framework.views import APIView
from rest_framework.response import Response
from .models import studentdb, hostel_info, marks_status, marks_subjects
import json
from django.shortcuts import HttpResponse
from rest_framework.decorators import api_view

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
    if request.method == 'POST':
        full_name = request.POST.get('full_name')
        enroll_no = request.POST.get('enroll_no')
        program_name = request.POST.get('program_name')
        school = request.POST.get('school')
        roll_no = request.POST.get('roll_no')
        father_name = request.POST.get('father_name')
        mother_name = request.POST.get('mother_name')
        dob = request.POST.get('dob')
        sex = request.POST.get('sex')
        email = request.POST.get('email')
        phone = request.POST.get('phone')

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
        response_data['message'] = 'fail'

    return HttpResponse(json.dumps(response_data), content_type="application/json")


def addhostelinfo(request):
    response_data = {}
    if request.method == 'GET':
        roll_num = request.GET.get('roll_num')
        hostel_name = request.GET.get('hostel_name')
        room_num = request.GET.get('room_num')
        warden_name = request.GET.get('warden_name')
        warden_mob = request.GET.get('warden_mob')
        caretaker_name = request.GET.get('caretaker_name')
        caretaker_num = request.GET.get('caretaker_num')

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
        response_data['message'] = 'fail'

    return HttpResponse(json.dumps(response_data), content_type="application/json")


def addmarksinfo(request):
    response_data = {}
    if request.method == 'GET':
        roll_num = request.GET.get('roll_num')
        subject_code = request.GET.get('subject_code')
        grade = request.GET.get('grade')
        tc = request.GET.get('tc')
        tgp = request.GET.get('tgp')
        sgpa = request.GET.get('sgpa')
        result = request.GET.get('result')
        semester = request.GET.get('semester')

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
        response_data['message'] = 'fail'

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



