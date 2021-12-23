import { Component, OnInit } from '@angular/core';
import { User } from 'src/app/Model/User';
import { UserRestService } from 'src/app/services/rest/user/user.rest.service';
import { UserService } from 'src/app/services/user/user.service';

@Component({
  selector: 'app-users-admin',
  templateUrl: './users-admin.component.html',
  styleUrls: ['./users-admin.component.css']
})
export class UsersAdminComponent implements OnInit {

  users!: User[];

  constructor(private userService: UserRestService) { }

  ngOnInit(): void {
    this.fetchUsers();
  }

  fetchUsers() {
    this.userService.getAllUsers().subscribe(
      data => this.users = data
    ),
    (error: any) => (
      console.log(error)
    )
  }

}
