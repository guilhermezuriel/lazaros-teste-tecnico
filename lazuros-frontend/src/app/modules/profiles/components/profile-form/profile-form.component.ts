import { Component, OnInit, Inject } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Profile } from '../../../../models/profile.model';
import { ProfileService } from '../../../../services/profile.service';

@Component({
  selector: 'app-profile-form',
  templateUrl: './profile-form.component.html',
  styleUrls: ['./profile-form.component.scss']
})
export class ProfileFormComponent implements OnInit {
  form: FormGroup;
  isEdit = false;
  isSubmitting = false;

  constructor(
    private fb: FormBuilder,
    private profileService: ProfileService,
    private dialogRef: MatDialogRef<ProfileFormComponent>,
    private snackBar: MatSnackBar,
    @Inject(MAT_DIALOG_DATA) public data: Profile
  ) {
    this.isEdit = !!data;
  }

  ngOnInit() {
    this.form = this.fb.group({
      descricao: [this.data ? this.data.descricao : '', [Validators.required, Validators.minLength(5)]]
    });
  }

  onSubmit(): void {
    if (this.form.invalid) {
      return;
    }

    this.isSubmitting = true;
    const profileData = this.form.value;

    const request = this.isEdit
      ? this.profileService.update(this.data.id, profileData)
      : this.profileService.create(profileData);

    request.subscribe({
      next: () => {
        this.snackBar.open(
          this.isEdit ? 'Perfil atualizado com sucesso' : 'Perfil criado com sucesso',
          'Fechar',
          { duration: 3000 }
        );
        this.dialogRef.close(true);
      },
      error: (error) => {
        console.error('Erro ao salvar perfil:', error);
        this.snackBar.open('Erro ao salvar perfil', 'Fechar', { duration: 3000 });
        this.isSubmitting = false;
      }
    });
  }

  onCancel(): void {
    this.dialogRef.close(false);
  }

  getErrorMessage(field: string): string {
    const control = this.form.get(field);
    if (control && control.hasError('required')) {
      return 'Campo obrigatório';
    }
    if (control && control.hasError('minlength')) {
      return 'Mínimo de 5 caracteres';
    }
    return '';
  }
}
