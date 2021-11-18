import React, { useState } from "react";

import {
  Flex,
  VStack,
  Heading,
  Input,
  Button,
  FormControl,
  FormLabel,
} from "@chakra-ui/react";

function Login() {
  const [email, setEmail] = useState();
  const [password, setPassword] = useState();
  const [error, setError] = useState();

  function validateEmail(email) {
    return email !== "";
  }

  function validatePassword(password) {
    return password !== "";
  }

  function handleSubmit(event) {
    setError("");
    console.log(`error: ${error}`); // seems like error is not updated immediately

    if (!validateEmail(email)) {
      setError("email");
    }
    if (!validatePassword(password)) {
      setError("password");
    }

    if (error) {
      alert(`error: ${error}`);
      setPassword("");
    } else {
      const credentials = { email: email, passworld: password };
      alert("login data\n" + JSON.stringify(credentials));
    }
  }

  // TODO use Formik library instead of implementing Form manually

  return (
    <Flex bg="gray.700" height="100vh" alignItems="center" justify="center">
      <VStack
        spacing={4}
        rounded="lg"
        w="full"
        maxW="sm"
        my="20"
        p="10"
        bg="gray.100"
        textColor="gray.800"
        boxShadow="dark-lg"
      >
        <Heading color="black">Sign In</Heading>
        <FormControl id="email" isRequired>
          <FormLabel>Email</FormLabel>
          <Input
            type="email"
            variant="outline"
            bg="gray.300"
            placeholder="E-Mail"
            _placeholder={{ color: "gray.500" }}
            value={email}
            onChange={(event) => setEmail(event.target.value)}
          />
        </FormControl>

        <FormControl id="password" isRequired isInvalid={error === "password"}>
          <FormLabel>Password</FormLabel>
          <Input
            type="password"
            variant="outline"
            bg="gray.300"
            placeholder="Password"
            _placeholder={{ color: "gray.500" }}
            value={password}
            onChange={(event) => setPassword(event.target.value)}
          />
        </FormControl>

        <Button
          type="submit"
          bg="teal.400"
          onClick={handleSubmit}
          _hover={{ bg: "teal.300" }}
          w="20"
        >
          Login
        </Button>
      </VStack>
    </Flex>
  );
}

export default Login;
