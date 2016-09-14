from rest_framework import serializers
from .models import studentdb


class StudentSerializer(serializers.ModelSerializer):
    class Meta:
        model = studentdb
        fields = ('cd', 'cdname', 'cname', 'roll', 'fname', 'mname', 'dob', 'gender', 'cat', 'state', 'rank', 'mobile', 'emailid')


class AdminSerializer(serializers.ModelSerializer):
    class Meta:
        model = studentdb
        fields = ('cd', 'cdname', 'cname', 'roll', 'fname', 'mname', 'dob', 'gender', 'cat', 'state', 'rank', 'mobile', 'emailid', 'ddbank',
                  'ddno', 'dddate', 'isdd', 'isfee', 'isreg')


