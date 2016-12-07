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
from student_info.models import studentdb, hostel_info
from student_info.serializer import HostelSerializer
from user_account.views import generateLogs


class Hostel_Detail(generics.RetrieveAPIView):
    queryset = hostel_info.objects.all()
    lookup_field = 'roll_num'
    serializer_class = HostelSerializer


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
