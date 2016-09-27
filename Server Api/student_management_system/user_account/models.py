from django.db import models


class UserSMS(models.Model):
    email = models.EmailField(max_length=200, null= True)
    password = models.CharField(max_length=200)
    username = models.CharField(max_length=10, null=True)
    type = models.CharField(max_length=10, null= True)
    roll_no = models.CharField(max_length=10, null=True)
