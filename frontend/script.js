const PHOTOS_API_URL = 'http://localhost:8080/api/v1/photo';


fetch(PHOTOS_API_URL)
  .then(response => response.json())
  .then(data => {
    const photoListElement = document.getElementById('photoList');
    data.forEach(photo => {
      const listItem = document.createElement('li');
      listItem.textContent = `${photo.title} - ${photo.description}`;
      photoListElement.appendChild(listItem);
    });
  })
  .catch(error => {
    console.error('Errore durante la richiesta delle foto:', error);
  });


  const searchForm = document.getElementById('searchForm');
  const searchInput = document.getElementById('searchInput');
  const photoListElement = document.getElementById('photoList');

  searchForm.addEventListener('submit', event => {


    fetch(PHOTOS_API_URL + `?keyword=` + encodeURIComponent(keyword))
      .then(response => response.json())
      .then(data => {
        photoListElement.innerHTML = '';

        data.forEach(photo => {
          const listItem = document.createElement('li');
          listItem.textContent = `${photo.title} - ${photo.description}`;
          photoListElement.appendChild(listItem);
        });
      })
      .catch(error => {
        console.error('Errore durante la richiesta delle foto:', error);
      });
  });


const urlParams = new URLSearchParams(window.location.search);
const photoId = urlParams.get('id');

fetch(PHOTOS_API_URL + `/` + photoId)
  .then(response => response.json())
  .then(photo => {
    const titleElement = document.getElementById('title');
    titleElement.textContent = photo.title;

    const descriptionElement = document.getElementById('description');
    descriptionElement.textContent = photo.description;

    const imageElement = document.getElementById('image');
    imageElement.src = photo.url;
    imageElement.alt = photo.title;
  })
  .catch(error => {
    console.error('Errore durante la richiesta dei dettagli della foto:', error);
  });


const contactForm = document.getElementById('contactForm');
const emailInput = document.getElementById('emailInput');
const messageInput = document.getElementById('messageInput');

contactForm.addEventListener('submit', event => {
  event.preventDefault();

  const message = {
    email: emailInput.value,
    message: messageInput.value
  };

  axios.post('/api/v1/contact', message)
    .then(response => {
      console.log('Messaggio inviato:', response.data);
    })
    .catch(error => {
      console.error('Errore durante l\'invio del messaggio:', error);
    });
});

