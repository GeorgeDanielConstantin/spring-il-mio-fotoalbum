<script>
import axios from "axios";

export default {
  data() {
    return {
      photos: [],
      filteredPhotos: [],
      search: "",
      contactForm: {
        email: "",
        message: "",
      },
    };
  },
  created() {
    this.fetchPhotos();
  },
  methods: {
    fetchPhotos() {
      axios
        .get("http://localhost:8080/api/v1/photo")
        .then((response) => {
          this.photos = response.data;
          this.filteredPhotos = response.data;
        })
        .catch((error) => {
          console.error(error);
        });
    },
    searchPhotos() {
      this.filteredPhotos = this.photos.filter((photo) =>
        photo.title.toLowerCase().includes(this.search.toLowerCase())
      );
    },
    submitForm() {
      axios
        .post("http://localhost:8080/api/v1/messages", {
          email: this.contactForm.email,
          message: this.contactForm.message,
        })
        .then((response) => {
          this.contactForm.email = "";
          this.contactForm.message = "";
          alert("Message saved successfully!");
        })
        .catch((error) => {
          console.error(error);
          alert("An error while saving the message.");
        });
    },
  },
};
</script>

<template>
  <div class="container">
    <h2>Photo Gallery</h2>

    <div class="input-group">
      <input
        @keyup.enter="searchPhotos"
        type="search"
        class="form-control"
        v-model="search"
        placeholder="Search by title"
      />
      <button class="btn btn-outline-secondary" @click="searchPhotos">
        Search
      </button>
    </div>

    <div class="row row-cols-md-3">
      <div class="col" v-for="photo in filteredPhotos" :key="photo.id">
        <div v-if="photo.visibility">
          <h3>{{ photo.title }}</h3>
          <img :src="photo.url" class="img-fluid" alt="" />
        </div>
      </div>
    </div>

    <h2>Contact Form</h2>
    <form @submit.prevent="submitForm">
      <label class="form-label" for="email">Email:</label>
      <input
        class="form-control mb-3"
        type="email"
        id="email"
        v-model="contactForm.email"
        required
      />

      <label class="form-label" for="message">Message:</label>
      <textarea
        class="form-control mb-3"
        id="message"
        v-model="contactForm.message"
        required
      ></textarea>

      <button class="btn btn-primary" type="submit">Send Message</button>
    </form>
  </div>
</template>
