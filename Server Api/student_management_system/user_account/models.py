from django.db import models
from django.contrib.auth.models import AbstractUser


class UserSMS(models.Model):
    email = models.EmailField(max_length=200, null= True)
    password = models.CharField(max_length=200)
    username = models.CharField(max_length=10, null=True)
    group_name = models.CharField(max_length=10, null= True)
    # roll_no = models.CharField(max_length=10, null=True)

    def __str__(self):
        return self.username


class UserGroup(models.Model):
    group_name = models.CharField(max_length = 200, null = True)
    description = models.CharField(max_length = 200, null = True)
    user_permit = models.CharField(max_length = 50, null = True)
    student_permit = models.CharField(max_length=50, null=True)
    hostel_permit = models.CharField(max_length=50, null=True)
    exam_permit = models.CharField(max_length=50, null=True)
    review_permit = models.CharField(max_length=50, null=True)

    def __str__(self):
        return self.group_name + ' ' + self.description


class Logs(models.Model):
    uname = models.CharField(max_length = 50, null = True)
    ugroup = models.CharField(max_length = 50, null = True)
    action = models.CharField(max_length = 200, null = True)
    datetime = models.DateTimeField()
    ipAddress = models.CharField(max_length=200, null=True)
    system = models.CharField(max_length=500, null=True)

    def __str__(self):
        return self.uname + '  =  ' + self.action

