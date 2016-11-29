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
    permissions = models.CharField(max_length = 50, null = True)

    def __str__(self):
        return self.group_name + ' ' + str(self.permissions)
