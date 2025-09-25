import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../environments/environment';

export type TaskStatus = 'PENDING' | 'COMPLETED';

export interface Task {
  id: number;
  title: string;
  description?: string;
  status: TaskStatus;
}

@Injectable({ providedIn: 'root' })
export class TaskService {
  private base = `${environment.apiUrl}/api/tasks`;

  constructor(private http: HttpClient) {}

  list() {
    return this.http.get<Task[]>(this.base);
  }

  create(title: string, description?: string) {
    return this.http.post<Task>(this.base, { title, description });
  }

  update(task: Task) {
    return this.http.put<Task>(`${this.base}/${task.id}`, task);
  }

  remove(id: number) {
    return this.http.delete<void>(`${this.base}/${id}`);
  }
}
