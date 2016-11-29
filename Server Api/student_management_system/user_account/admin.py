from django.contrib import admin
from .models import UserSMS, UserGroup


class UserGroupAdmin(admin.ModelAdmin):
    list_display = ('group_name', 'description', 'permissions')


class UserSMSAdmin(admin.ModelAdmin):
    list_display = ('username', 'group_name')


admin.site.register(UserSMS, UserSMSAdmin)
admin.site.register(UserGroup, UserGroupAdmin)