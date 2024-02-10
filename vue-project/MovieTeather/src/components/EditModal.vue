<template>
    <div >
        <b-button style="width: 100%; padding: 8px" v-b-modal="`modalEdicion_${pelicula.id}`" @click="fillForm">Editar</b-button>
        <b-modal :id="`modalEdicion_${pelicula.id}`" title="Editar Pelicula" hide-footer>
            <form id="form" @submit.prevent="checkForm">
                <p v-if="errors.length">
                    <b>Por favor, corrija el(los) siguiente(s) error(es):</b>
                    <ul>
                        <li v-for="error in errors" :key="error">{{ error }}</li>
                    </ul>
                </p>
                <p>
                    <label for="name">Nombre: </label>
                    <input id="name" v-model="name" type="text" name="name">
                </p>
                <p>
                    <label for="description">Descripción: </label>
                    <input id="description" v-model="description" type="text" name="description">
                </p>
                <p>
                    <label for="category">Categoría</label>
                    <select id="category" v-model="selected_category" name="category">
                        <option v-for="category in categories" :key="category.id" :value="category.id">
                            {{ category.name }}
                        </option>
                    </select>
                </p>
                <p>
                    <input type="submit" value="Guardar Cambios">
                </p>
            </form>
            <button @click="closeModal">Cancelar</button>
        </b-modal>
    </div>
</template>

<script>
import axios from 'axios'
export default {
  name: "EditModal",
   props: {
    pelicula: {
      type: Object,
      required: true
    },
  },
  data() {
    return {
      errors: [],
      name: null,
      description: null,
      selected_category: null,
      categories: []
    };
  },
  methods: {
    fillForm() {
        this.name = this.pelicula.name;
        this.description = this.pelicula.description;
        this.selected_category = this.pelicula.categoria.id;
    },
    checkForm() {
      if (this.name && this.description && this.selected_category) {
        this.sendForm()
        return true;
      }

      this.errors = [];

      if (!this.name) {
        this.errors.push('El nombre es obligatorio.');
      }
      if (!this.description) {
        this.errors.push('La descripcion es obligatoria.');
      }
      if (!this.selected_category) {
        this.errors.push('Debes seleccionar una categoria.');
      }
    },
    sendForm() {
        axios.patch(`http://localhost:8080/api/peliculas/${this.pelicula.id}`, {
            name: this.name,
            description: this.description,
            categoria: {
                id: this.selected_category
            }
        })
        .then((response) => {  
            console.log(response);
            this.$emit('actualizacionExitosa');
            this.$swal({
                title: "Actualizada",
                text: "La película ha sido actualizada con éxito",
                icon: "success",
            });
            this.$root.$emit('bv::hide::modal', `modalEdicion_${this.pelicula.id}`); 
        })
        .catch((error) => {  
            console.log(error);
        });
    },
    closeModal(){
        this.$root.$emit('bv::hide::modal', `modalEdicion_${this.pelicula.id}`); 
    }
  },
  mounted() {
    axios
      .get("http://localhost:8080/api/categorias")
      .then((response) => {
        this.categories = response.data;
      })
      .catch((e) => {
        console.error("Error en la peticion: ", e);
      });
  },
};
</script>
