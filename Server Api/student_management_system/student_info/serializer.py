from rest_framework import serializers
from .models import studentdb


class StudentSerializer(serializers.ModelSerializer):
    class Meta:
        model = studentdb
        fields = ('full_name', 'enroll_no', 'program_name', 'school', 'roll_no', 'father_name', 'mother_name', 'dob', 'sex', 'email', 'phone')


class AdminSerializer(serializers.ModelSerializer):
    class Meta:
        model = studentdb
        fields = ('full_name', 'enroll_no', 'program_name', 'school', 'roll_no', 'father_name', 'mother_name', 'dob', 'sex', 'email', 'phone')


