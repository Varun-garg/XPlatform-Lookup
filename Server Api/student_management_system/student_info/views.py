from django.shortcuts import render
from .serializer import AdminSerializer, StudentSerializer
from rest_framework import generics
from .models import studentdb

# These will be used in the admin section


class AdminStudent_List(generics.ListAPIView):
    queryset = studentdb.objects.all()
    serializer_class = AdminSerializer


class AdminStudent_Detail(generics.RetrieveAPIView):
    queryset = studentdb.objects.all()
    lookup_field = 'roll'
    serializer_class = AdminSerializer


# These will be used in the student section


class Student_Detail(generics.RetrieveAPIView):
    queryset = studentdb.objects.all()
    lookup_field = 'roll'
    serializer_class = StudentSerializer
