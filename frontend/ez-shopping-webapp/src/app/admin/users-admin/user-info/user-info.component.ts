import {Component, EventEmitter, Input, OnDestroy, OnInit, Output} from '@angular/core';
import {User} from "../../../Model/User";

@Component({
  selector: 'app-user-info',
  templateUrl: './user-info.component.html',
  styleUrls: ['./user-info.component.css']
})
export class UserInfoComponent implements OnInit, OnDestroy {

  @Input()
  user!: User;

  @Output()
  submitted = new EventEmitter();

  constructor() { }

  ngOnInit(): void {
  }

  ngOnDestroy(): void {
    this.submitted.unsubscribe();
  }

  close() {
    this.submitted.emit();
  }
}
