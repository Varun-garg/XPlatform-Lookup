from django.conf.urls import url
from . import views


urlpatterns = [
    url(r'^login', views.user_login, name="login"),
    url(r'^new_usergroup', views.new_usergroup, name="new_usergroup"),
    url(r'^new_user', views.new_user_registration, name="new_user_signup"),
    url(r'^logout', views.user_logout, name="user_logout"),
]