FROM nginx:latest
ADD ../../frontend/manager/dist/css /usr/share/nginx/html/css
ADD ../../frontend/manager/dist/js /usr/share/nginx/html/js
ADD ../../frontend/manager/dist/favico.ico /usr/share/nginx/html/icon
RUN rm -rf /usr/share/nginx/html/index.html
ADD ././../frontend/manger/dist/index.html /usr/share/nginx/html/index.html
RUN rm -rf /etc/nginx/conf.d/default.conf
ADD ../../frontend/manager/nginx/default.conf /etc/nginx/conf.d/default.conf
CMD ["nginx", "-g", "daemon off;"]