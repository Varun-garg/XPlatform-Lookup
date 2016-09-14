from django.shortcuts import HttpResponse
import json
from .models import UserSMS
from django.core.context_processors import csrf
from django.views.decorators.csrf import csrf_protect


@csrf_protect
def user_login(request):
    if request.method == 'GET':
        username = request.GET.get('email', '')
        password = request.GET.get('password', '')
        user = UserSMS.objects.get(username = username)
        response_data = {}

        if user is None:
            response_data['message'] = 'fail'
            return HttpResponse(json.dumps(response_data), content_type="application/json")
        else:
            dbpass = user.password
            if dbpass == password:
                response_data['message'] = 'success'
                return HttpResponse(json.dumps(response_data), content_type="application/json")
            else:
                response_data['message'] = 'fail'
                return HttpResponse(json.dumps(response_data), content_type="application/json")
