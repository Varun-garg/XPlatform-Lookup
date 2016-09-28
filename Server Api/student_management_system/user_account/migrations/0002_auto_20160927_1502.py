# -*- coding: utf-8 -*-
# Generated by Django 1.9.8 on 2016-09-27 09:32
from __future__ import unicode_literals

from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ('user_account', '0001_initial'),
    ]

    operations = [
        migrations.AddField(
            model_name='usersms',
            name='email',
            field=models.EmailField(max_length=200, null=True),
        ),
        migrations.AddField(
            model_name='usersms',
            name='type',
            field=models.CharField(max_length=10, null=True),
        ),
        migrations.AlterField(
            model_name='usersms',
            name='username',
            field=models.CharField(max_length=10, null=True),
        ),
    ]