import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { TableModule } from 'primeng/table';
import { ButtonModule } from 'primeng/button';
import { TagModule } from 'primeng/tag';
import { CardModule } from 'primeng/card';
import { InputTextModule } from 'primeng/inputtext';
import { Task, TaskService, TaskStatus } from '../../../core/task.service';
import { AuthService } from '../../../core/auth.service';

@Component({
  selector: 'app-task-dashboard',
  standalone: true,
  imports: [CommonModule, FormsModule, TableModule, ButtonModule, TagModule, CardModule, InputTextModule],
  templateUrl: './task-dashboard.component.html',
  styleUrls: ['./task-dashboard.component.scss']
})
export class TaskDashboardComponent implements OnInit {
  tasks: Task[] = [];
  form = { title: '', description: '' };

  constructor(private tasksApi: TaskService, private auth: AuthService) { }

  ngOnInit() { this.load(); }

  load() {
    this.tasksApi.list().subscribe(res => this.tasks = res);
  }

  create() {
    this.tasksApi.create(this.form.title, this.form.description).subscribe(() => {
      this.form = { title: '', description: '' };
      this.load();
    });
  }

  toggle(task: Task) {
    const updated: Task = {
      ...task,
      status: task.status === 'PENDING' ? 'COMPLETED' : 'PENDING' as TaskStatus
    };
    this.tasksApi.update(updated).subscribe(() => this.load());
  }

  delete(task: Task) {
    this.tasksApi.remove(task.id).subscribe(() => this.load());
  }

  logout() {
    this.auth.logout();
    location.href = '/login';
  }
}
