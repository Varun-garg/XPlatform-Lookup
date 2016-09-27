from django.db import models
from django.utils import timezone


class studentdb(models.Model):
    full_name = models.CharField(max_length=100, null= True)
    enroll_no = models.IntegerField(null= True)
    program_name = models.CharField(max_length=100,null= True)
    school = models.CharField(max_length=200,null= True)
    roll_no = models.CharField(max_length=20,null= True)
    father_name = models.CharField(max_length=200,null= True)
    mother_name = models.CharField(max_length=200,null= True)
    dob = models.DateField(null= True)
    sex = models.CharField(max_length=6,null= True)
    email = models.EmailField(max_length=100,null= True)
    phone = models.IntegerField(null= True)

