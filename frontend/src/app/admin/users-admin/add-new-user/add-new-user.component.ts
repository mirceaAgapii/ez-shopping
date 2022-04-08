import {Component, EventEmitter, OnInit, Output} from '@angular/core';
import {User} from "../../../Model/User";
import {UserRestService} from "../../../services/rest/user/user.rest.service";

@Component({
  selector: 'app-add-new-user',
  templateUrl: './add-new-user.component.html',
  styleUrls: ['./add-new-user.component.css']
})
export class AddNewUserComponent implements OnInit {
  newUser: User = new User();
  checkPassword!: String;

  roles!: String[];

  @Output()
  submitted = new EventEmitter();

  constructor(private userRestService: UserRestService) { }

  ngOnInit(): void {
    this.roles = ['CLIENT', 'ADMINISTRATOR', 'PRICE_CHECK', 'SUPERVISOR'];
  }

  addUser() {
    this.userRestService.register(this.newUser);
    this.cancel();
  }

  cancel() {
    this.submitted.emit();
  }

}
