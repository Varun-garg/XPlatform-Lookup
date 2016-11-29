from rest_framework import serializers
from .models import Logs


class LogsSerializer(serializers.ModelSerializer):
    class Meta:
        model = Logs
        fields = ('uname', 'ugroup', 'action', 'datetime','ipAddress','system')

