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


class hostel_info(models.Model):
    roll_num = models.CharField(max_length=100, null= True)
    hostel_name = models.CharField(max_length=100 ,null= True)
    room_num = models.CharField(max_length=100,null= True)
    warden_name = models.CharField(max_length=200,null= True)
    warden_mob = models.IntegerField(null= True)
    caretaker_name = models.CharField(max_length=200,null= True)
    caretaker_num = models.IntegerField(null= True)

