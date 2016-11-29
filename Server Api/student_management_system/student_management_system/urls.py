from django.conf.urls import url, include
from django.contrib import admin
from user_account import views

urlpatterns = [
    url(r'^admin/', admin.site.urls),
    url(r'^data/', include('student_info.urls')),
    url(r'^user/', include('user_account.urls')),
    url(r'^logs/', views.LogsList.as_view()),
]
