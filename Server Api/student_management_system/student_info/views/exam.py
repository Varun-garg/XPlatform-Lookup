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
from student_info.models import studentdb, marks_status, marks_subjects
from student_info.serializer import MarksStatusSerializer, MarksSubjectsSerializer
from user_account.views import generateLogs


class Student_Exams(APIView):

    def get(self, request, **kwargs):
        subjects = marks_subjects.objects.filter(roll_num=kwargs['roll_num'], semester=kwargs['semester'])
        status = marks_status.objects.filter(roll_num=kwargs['roll_num'], semester=kwargs['semester'])
        subjects_serializer = MarksSubjectsSerializer(subjects, many=True)
        status_serializer = MarksStatusSerializer(status, many=True)
        return Response({'Marks Summary': subjects_serializer.data, 'Overall Result': status_serializer.data,})


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
