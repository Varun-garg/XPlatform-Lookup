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
    sex = models.CharField(max_length=6, null= True)
    email = models.EmailField(max_length=100,null= True)
    phone = models.IntegerField(null= True)

    def __str__(self):
        if self.roll_no is None:
            self.roll_no = 'NULL/None'
        if self.full_name is None:
            self.full_name = 'NULL/None'
        desc = self.roll_no + "-" + self.full_name
        return desc


class hostel_info(models.Model):
    roll_num = models.CharField(max_length=100, null= True)
    hostel_name = models.CharField(max_length=100, null= True)
    room_num = models.CharField(max_length=100, null= True)
    warden_name = models.CharField(max_length=200, null= True)
    warden_mob = models.IntegerField(null = True)
    caretaker_name = models.CharField(max_length=200, null= True)
    caretaker_num = models.IntegerField(null= True)

    def __str__(self):
        if self.roll_num is None:
            self.roll_num = 'NULL/None'
        if self.hostel_name is None:
            self.hostel_name = 'NULL/None'
        desc = self.roll_num + "-" + self.hostel_name
        return desc


class marks_subjects(models.Model):
    roll_num = models.CharField(max_length=100, null=True)
    subject_code = models.CharField(max_length=10, null=True)
    grade = models.CharField(max_length=10, null=True)
    semester = models.PositiveIntegerField(null=True)

    def __str__(self):
        if self.roll_num is None:
            self.roll_num = 'NULL/None'
        if self.subject_code is None:
            self.subject_code = 'NULL/None'
        desc = self.roll_num + "-" + self.subject_code
        return desc


class marks_status(models.Model):
    roll_num = models.CharField(max_length=100, null=True)
    tc = models.PositiveSmallIntegerField(null=True)
    tgp = models.PositiveSmallIntegerField(null=True)
    sgpa = models.FloatField(null=True)
    result = models.CharField(max_length=50,null = True)
    semester = models.PositiveIntegerField(null=True)

    def __str__(self):
        if self.roll_num is None:
            self.roll_num = 'NULL/None'
        if self.result is None:
            self.result = 'NULL/None'
        desc = self.roll_num + "-" + self.result
        return desc


class SubmitReview(models.Model):
    student = models.CharField(max_length=100)
    section = models.CharField(max_length=20)
    comment = models.CharField(max_length=1000)
    user = models.CharField(max_length=100)
    date = models.DateTimeField(auto_now_add=True)

    def __str__(self):
        if self.student is None:
            self.student = 'NULL/None'
        if self.comment is None:
            self.comment = 'NULL/None'
        desc = self.student + "-" + self.comment
        return desc




