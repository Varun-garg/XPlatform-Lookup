# -*- coding: utf-8 -*-
# Generated by Django 1.9.8 on 2016-11-29 07:05
from __future__ import unicode_literals

from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ('user_account', '0009_auto_20161129_0703'),
    ]

    operations = [
        migrations.AlterField(
            model_name='usergroup',
            name='permissions',
            field=models.CharField(max_length=50, null=True),
        ),
    ]
