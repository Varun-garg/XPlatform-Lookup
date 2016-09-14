from django.conf.urls import url, include
from django.contrib import admin

urlpatterns = [
    url(r'^admin/', admin.site.urls),
    url(r'^data/', include('student_info.urls')),
    url(r'^user/', include('user_account.urls')),
]
