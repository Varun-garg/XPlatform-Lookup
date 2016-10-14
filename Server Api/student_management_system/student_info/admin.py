from django.contrib import admin
from .models import studentdb, hostel_info, marks_status, marks_subjects


admin.site.register(studentdb)
admin.site.register(hostel_info)
admin.site.register(marks_status)
admin.site.register(marks_subjects)

