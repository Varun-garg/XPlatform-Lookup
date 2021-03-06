# -*- coding: utf-8 -*-
# Generated by Django 1.9.8 on 2016-10-14 13:58
from __future__ import unicode_literals

from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ('student_info', '0005_auto_20160928_0100'),
    ]

    operations = [
        migrations.CreateModel(
            name='marks_status',
            fields=[
                ('id', models.AutoField(auto_created=True, primary_key=True, serialize=False, verbose_name='ID')),
                ('roll_no', models.CharField(max_length=100)),
                ('tc', models.PositiveSmallIntegerField(null=True)),
                ('tgp', models.PositiveSmallIntegerField(null=True)),
                ('sgpa', models.FloatField(null=True)),
                ('result', models.CharField(max_length=50, null=True)),
                ('semester', models.PositiveIntegerField(null=True)),
            ],
        ),
        migrations.CreateModel(
            name='marks_subjects',
            fields=[
                ('id', models.AutoField(auto_created=True, primary_key=True, serialize=False, verbose_name='ID')),
                ('roll_no', models.CharField(max_length=100, null=True)),
                ('subject_code', models.CharField(max_length=10, null=True)),
                ('grade', models.CharField(max_length=10, null=True)),
                ('semester', models.PositiveIntegerField(null=True)),
            ],
        ),
    ]
