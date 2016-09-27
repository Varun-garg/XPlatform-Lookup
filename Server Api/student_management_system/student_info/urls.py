from django.conf.urls import url
from . import views
from rest_framework.urlpatterns import format_suffix_patterns


urlpatterns = [
    url(r'^student/(?P<roll_no>[\w-]+)/$', views.Student_Detail.as_view()),
    url(r'^admin/(?P<roll_no>[\w-]+)/$', views.AdminStudent_Detail.as_view()),
    url(r'admin/$', views.AdminStudent_List.as_view()),
]

urlpatterns = format_suffix_patterns(urlpatterns)