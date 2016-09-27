from django.shortcuts import HttpResponse
import json
from .models import UserSMS
from django.views.decorators.csrf import csrf_protect
from django.db.models.query_utils import Q


@csrf_protect
def user_login(request):
    if request.method == 'GET':
        username = request.GET.get('username', '')
        email = request.GET.get('email', '')
        password = request.GET.get('password', '')
        user = UserSMS.objects.get(Q(email=email)|Q(username=username))
        response_data = {}

        # if user is None:
        #     response_data['message'] = 'fail'
        #     return HttpResponse(json.dumps(response_data), content_type="application/json")
        if user is not None:
            print(user.password)
            dbpass = user.password
            if dbpass == password:
                response_data['type'] = user.type
                response_data['email'] = user.email
                response_data['username'] = user.username
                response_data['roll_no'] = user.roll_no
                response_data['message'] = 'success'
                return HttpResponse(json.dumps(response_data), content_type="application/json")
            else:
                response_data['message'] = 'fail'
                return HttpResponse(json.dumps(response_data), content_type="application/json")
        else:
            response_data['message'] = 'fail'
            return HttpResponse(json.dumps(response_data), content_type="application/json")
