from django.db import models
from django.utils import timezone


class studentdb(models.Model):
    cd = models.CharField(max_length=5)
    cdname = models.CharField(max_length=200)
    form = models.IntegerField()
    cname = models.CharField(max_length=200)
    roll = models.CharField(max_length=20)
    password = models.CharField(max_length=200)
    fname = models.CharField(max_length=200)
    mname = models.CharField(max_length=200)
    dob = models.DateField()
    gender = models.CharField(max_length=6)
    cat = models.CharField(max_length=100)
    state = models.CharField(max_length=20)
    subcat = models.CharField(max_length=50)
    stat = models.CharField(max_length=1)
    rank = models.IntegerField()
    fee = models.IntegerField()
    isfee = models.BooleanField()
    isreg = models.BooleanField()
    updatedon = models.DateTimeField(default=timezone.now)
    createdon = models.DateTimeField()
    choices = models.CharField(max_length=500)
    ddbank = models.CharField(max_length=500)
    ddno = models.IntegerField()
    dddate = models.DateField()
    isdd = models.BooleanField()
    mobile = models.IntegerField()
    emailid = models.EmailField()





