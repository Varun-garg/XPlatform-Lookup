from django.contrib import admin
from .models import UserSMS, UserGroup, Logs


class UserGroupAdmin(admin.ModelAdmin):
    list_display = ('group_name', 'description', 'user_permit', 'student_permit', 'hostel_permit', 'exam_permit', 'review_permit')


class UserSMSAdmin(admin.ModelAdmin):
    list_display = ('username', 'group_name')


class LogsAdmin(admin.ModelAdmin):
    list_display = ('uname', 'action', 'datetime')


admin.site.register(UserSMS, UserSMSAdmin)
admin.site.register(UserGroup, UserGroupAdmin)
admin.site.register(Logs, LogsAdmin)