from rest_framework import serializers
from .models import studentdb, hostel_info, marks_status, marks_subjects, SubmitReview


class StudentSerializer(serializers.ModelSerializer):
    class Meta:
        model = studentdb
        fields = ('full_name', 'enroll_no', 'program_name', 'school', 'roll_no', 'father_name', 'mother_name', 'dob', 'sex', 'email', 'phone')


class AdminSerializer(serializers.ModelSerializer):
    class Meta:
        model = studentdb
        fields = ('full_name', 'enroll_no', 'program_name', 'school', 'roll_no', 'father_name', 'mother_name', 'dob', 'sex', 'email', 'phone')


class HostelSerializer(serializers.ModelSerializer):
    class Meta:
        model = hostel_info
        fields = ('roll_num', 'room_num', 'warden_name', 'warden_mob', 'caretaker_name', 'caretaker_num')


class MarksStatusSerializer(serializers.ModelSerializer):
    class Meta:
        model = marks_status
        fields = ('roll_num', 'tc', 'tgp', 'sgpa', 'result', 'semester')


class MarksSubjectsSerializer(serializers.ModelSerializer):
    class Meta:
        model = marks_subjects
        fields = ('subject_code', 'grade')


class SubmitReviewSerializer(serializers.ModelSerializer):
    class Meta:
        model = SubmitReview
        fields = ('student', 'comment', 'user')

