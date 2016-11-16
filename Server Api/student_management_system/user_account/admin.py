from django.contrib import admin
from .models import UserSMS, UserGroup


class UserGroupAdmin(admin.ModelAdmin):
    list_display = ('group_name', 'section', 'permissions')

admin.site.register(UserSMS)
admin.site.register(UserGroup, UserGroupAdmin)