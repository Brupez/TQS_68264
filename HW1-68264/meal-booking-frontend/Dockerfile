FROM oven/bun:latest

WORKDIR /meal-booking-frontend

COPY package.json ./
COPY . ./

RUN curl -fsSL https://bun.sh/install | bash
RUN bun install --frozen-lockfile


EXPOSE 8080
CMD ["bun", "--bun" ,"run", "dev", "--port", "3000", "--hostname", "0.0.0.0"]