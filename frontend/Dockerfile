# Stage 1: Build Angular app
FROM node:20-alpine AS build
WORKDIR /app

COPY package*.json ./
RUN npm install

COPY . .
ENV NODE_OPTIONS="--max_old_space_size=4096"
RUN npm run build -- --configuration production

# Stage 2: Serve with Nginx
FROM nginx:alpine
WORKDIR /usr/share/nginx/html

# Copy compiled Angular dist (only the browser bundle)
COPY --from=build /app/dist/springangtasks-frontend/browser ./

# Copy custom nginx config
COPY nginx.conf /etc/nginx/conf.d/default.conf

EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]
