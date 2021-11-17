import React from "react";
import {
  Heading,
  Stack,
  Text,
  Button,
  Box,
  Flex,
  Spacer,
  VStack,
} from "@chakra-ui/react";

function Card(props) {

  return (
    <Flex
      bg="gray.700"
      rounded="lg"
      align="center"
      p={3}
      justifyContent="space-between"
    >
      <VStack>
        <Heading size="lg" color="teal">
          {" "}
          {props.title}
        </Heading>
        <Heading size="md" color="blue.400">
          {" "}
          {props.subtitle}
        </Heading>
      </VStack>
      <VStack>
        <Box p={6}>
          <Stack spacing={0} align={"center"} mb={5}>
            <Heading fontSize={"2xl"} fontWeight={500} fontFamily={"body"}>
              {props.productowner}
            </Heading>
            <Text color={"gray.500"}>Product Owner</Text>
          </Stack>

          <Stack direction={"row"} justify={"center"} spacing={6}>
            <Stack spacing={0} align={"center"}>
              <Text fontWeight={600}>{props.created}</Text>
              <Text fontSize={"sm"} color={"gray.500"}>
                Created
              </Text>
            </Stack>
            <Stack spacing={0} align={"center"}>
              <Text fontWeight={600}>{props.lastopened}</Text>
              <Text fontSize={"sm"} color={"gray.500"}>
                Last Opened
              </Text>
            </Stack>
          </Stack>
        </Box>
        <Spacer />

        <Button
          onclick={props.link}
          bg="teal.400"
          hover={{ bg: "teal.300" }}
          w="20"
          style={{ alignSelf: "flex-right" }}
        >
          Open
        </Button>
      </VStack>
    </Flex>
  );
}

export default Card;
