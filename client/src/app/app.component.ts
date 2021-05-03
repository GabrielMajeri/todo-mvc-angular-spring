import { Component, OnInit } from '@angular/core';
import { ToDo } from './to-do';
import { ToDoService } from './to-do.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
})
export class AppComponent implements OnInit {
  toDos: ToDo[] = [];

  constructor(private toDoService: ToDoService) {}

  ngOnInit(): void {
    this.toDos = this.toDoService.getToDos();
  }
}
