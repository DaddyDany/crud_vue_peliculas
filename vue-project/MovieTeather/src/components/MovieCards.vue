<template>
  <div class="container" style="height: 100vh">
    <div style="display: flex; padding-top: 50px; padding-bottom: 50px">
      <h1 style="margin-bottom: 0">Peliculas</h1>
      <Modal @registroExitoso="actualizarPeliculas"></Modal>
    </div>
    <div class="text-center" id="loaderSpinner" v-if="loading">
      <b-spinner class="spinner" type="grow"></b-spinner>
      <b-spinner class="spinner" type="grow"></b-spinner>
      <b-spinner class="spinner" type="grow"></b-spinner>
      <b-spinner class="spinner" type="grow"></b-spinner>
      <b-spinner class="spinner" type="grow"></b-spinner>
    </div>
    <div class="row">
      <div
        class="col-4 movieCards"
        v-for="pelicula in peliculas"
        :key="pelicula.id"
      >
        <div>
          <b-card
            :title="pelicula.name"
            img-src="https://picsum.photos/600/300/?image=25"
            img-alt="Image"
            img-top
            tag="article"
            style="max-width: 20rem"
            class="mb-2"
          >
            <b-card-text class="description">
              {{ pelicula.description }}
            </b-card-text>
            <div>
              <p class="CustomBadge">{{ pelicula.categoria.name }}</p>
            </div>
            <div style="width: 100%; display: flex">
              <div
                style="
                  display: flex;
                  width: 100%;
                  justify-content: space-around;
                "
              >
                <b-button
                  variant="danger"
                  style="width: 45%; padding: 8px"
                  @click="deleteMovie(pelicula.id)"
                  >Eliminar</b-button
                >
                <EditModal
                  style="width: 45%"
                  :key="'modalEdicion_' + pelicula.id"
                  :pelicula="pelicula"
                  @actualizacionExitosa="actualizarPeliculas"
                ></EditModal>
              </div>
            </div>
          </b-card>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import Modal from "./Modal.vue";
import EditModal from "./EditModal.vue";
import axios from "axios";
export default {
  name: "MovieCards",
  components: {
    Modal,
    EditModal,
  },
  data() {
    return {
      peliculas: [],
      loading: null,
    };
  },
  methods: {
    actualizarPeliculas() {
      axios
        .get("http://localhost:8080/api/peliculas")
        .then((response) => {
          this.peliculas = response.data;
          this.loading = false;
        })
        .catch((e) => {
          console.error("Error en la peticion: ", e);
        });
    },
    deleteMovie(id) {
      this.$swal({
        title: "¿Estas seguro?",
        text: "No podras revertir este cambio",
        icon: "warning",
        showCancelButton: true,
        confirmButtonColor: "#3085d6",
        cancelButtonColor: "#d33",
        cancelButtonText: "cancelar",
        confirmButtonText: "Si, eliminar",
      }).then((result) => {
        if (result.isConfirmed) {
          axios
            .delete(`http://localhost:8080/api/peliculas/${id}`)
            .then((response) => {
              this.$swal({
                title: "Eliminada",
                text: "La pelicula ha sido eliminada con exito",
                icon: "success",
              });
              this.actualizarPeliculas();
            })
            .catch((error) => {
              console.error(error);
            });
        }
      });
    },
  },
  mounted() {
    this.loading = true;
    setTimeout(() => {
      axios
        .get("http://localhost:8080/api/peliculas")
        .then((response) => {
          this.peliculas = response.data;
        })
        .catch((e) => {
          console.error("Error en la peticion: ", e);
        })
        .finally(() => {
          this.loading = false;
        });
    }, 1000);
  },
};
</script>

<style>
.description {
  height: 50px;
}

.CustomBadge {
  background-color: black;
  border-radius: 50px;
  color: white;
  text-align: center;
  width: 50%;
}
.movieCards {
  display: flex;
  justify-content: center;
}
#loaderSpinner {
  height: 75%;
  display: flex;
  justify-content: center;
  align-items: center;
}
.spinner {
  margin: 7px;
}
</style>
