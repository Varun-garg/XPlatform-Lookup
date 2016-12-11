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
from student_info.models import studentdb, SubmitReview
from student_info.serializer import SubmitReviewSerializer
from user_account.views import generateLogs


class Review(generics.ListAPIView):
    serializer_class = SubmitReviewSerializer

    def get_queryset(self):
        var = self.request.session.get('permissions')
        sec1 = ''
        sec2 = ''
        sec3 = ''
        if var['student_permit']=='2' or var['student_permit']=='3':
            sec1 = 'personal'
        if var['hostel_permit']=='2' or var['hostel_permit']=='3':
            sec2 = 'hostel'
        if var['exam_permit'] == '2' or var['exam_permit'] == '3':
            sec3 = 'exam'
        return SubmitReview.objects.filter(Q(section=sec1) | Q(section=sec2) | Q(section=sec3))



#student centric reviews
"""
class Review(APIView):
    def get(self, request, **kwargs):
        reviews = SubmitReview.objects.filter(student = kwargs['student'])
        review_result = SubmitReviewSerializer(reviews, many=True)
        return Response({'Review': review_result.data,})
"""


@api_view(['POST'])
def add_review(request):
    response_data = {}
    errors = []
    var = request.session.get('permissions')['review_permit']
    if (var is not None) and (var == '2' or var == '3'):
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