from django.conf.urls import url
from . import views
from rest_framework.urlpatterns import format_suffix_patterns


urlpatterns = [
    url(r'^student/search/$', views.studentSearch),
    url(r'^student/new/$', views.addstudent),
    url(r'^student/new/hostel/$', views.addhostelinfo),
    url(r'^student/new/exam/$', views.addmarksinfo),
    url(r'^student/new/review/$', views.add_review),
    url(r'^student/(?P<roll_no>[\w-]+)/$', views.Student_Detail.as_view()),
    url(r'^student/hostel/(?P<roll_num>[\w-]+)/$', views.Hostel_Detail.as_view()),
    url(r'^student/exam/(?P<roll_num>[\w-]+)/(?P<semester>[0-9]+)$', views.Student_Exams.as_view()),
    url(r'^student/review/(?P<student>[\w-]+)/$', views.Review.as_view()),
    url(r'^admin/(?P<roll_no>[\w-]+)/$', views.AdminStudent_Detail.as_view()),
    url(r'admin/$', views.AdminStudent_List.as_view()),
]

urlpatterns = format_suffix_patterns(urlpatterns)