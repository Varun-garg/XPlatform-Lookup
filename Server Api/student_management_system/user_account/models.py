from django.db import models


class UserSMS(models.Model):
    username = models.EmailField(max_length=200)
    password = models.CharField(max_length=200)

