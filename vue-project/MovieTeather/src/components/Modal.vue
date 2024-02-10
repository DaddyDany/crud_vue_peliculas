<template>
    <div style="margin-left: auto">
        <b-button v-b-modal.modal-1>Agregar Pelicula</b-button>
        <b-modal id="modal-1" title="Agregar Pelicula" hide-footer>
            <form
                id="form"
                @submit.prevent="checkForm"
            >
                <p v-if="errors.length">
                <b>Por favor, corrija el(los) siguiente(s) error(es):</b>
                <ul>
                    <li v-for="error in errors" :key="error">{{ error }}</li>
                </ul>
                </p>

                <p>
                <label for="name">Nombre: </label>
                <input
                    id="name"
                    v-model="name"
                    type="text"
                    name="name"
                >
                </p>

                <p>
                <label for="description">Descripción: </label>
                <input
                    id="description"
                    v-model="description"
                    type="text"
                    name="description"
                >
                </p>

                <p>
                <label for="category">Categoría</label>
                <select
                    id="category"
                    v-model="selected_category"
                    name="category"
                >
                    <option v-for="category in categories" :key="category.id" :value="category.id">
                    {{ category.name }}
                    </option>
                </select>
                </p>

                <p>
                <button type="submit">Enviar</button>
                <button @click="closeModal">Cancelar</button>
                </p>
            </form>
            
        </b-modal>
    </div>
</template>

<script>
import axios from 'axios'
export default {
  name: "Modal",
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
        this.errors.push('La edad es obligatoria.');
      }
      if (!this.selected_category) {
        this.errors.push('Debes seleccionar una categoria.');
      }
    },
    sendForm(){
        axios.post('http://localhost:8080/api/peliculas', {
            name: this.name,
            description: this.description,
            categoria: {
                id: this.selected_category
            }
        })
        .then((response) => {  
            this.name = null
            this.description = null
            this.selected_category= null
            this.errors = []
            this.$emit('registroExitoso');
            this.$swal({
                title: "Agregada",
                text: "La pelicula ha sido agregada con exito",
                icon: "success",
            });
            this.$root.$emit('bv::hide::modal', 'modal-1'); 
        })
        .catch((error) => {  
            console.log(error);
        });
    },
    closeModal(){
      this.$root.$emit('bv::hide::modal', 'modal-1'); 
      this.errors = [];
      console.log(this.errors)
      this.name = null;
      this.description = null;
      this.selected_category = null;
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
