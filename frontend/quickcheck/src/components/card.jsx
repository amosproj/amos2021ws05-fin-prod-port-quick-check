import React from "react";
import {
  Heading,
  Stack,
  Text,
  Button,
  Flex,
  SimpleGrid,
} from "@chakra-ui/react";



function CardLabel(props) {
  return (
    <Stack spacing={0} align={"center"}>
      <Text fontWeight={600} fontSize='md'>{props.value}</Text>
      <Text fontSize={"sm"} color={"gray.500"}>{props.name}
      </Text>
    </Stack>
  )
}


// Todo responsive: low width -> labels under title
function Card(props) {

  return (
    <SimpleGrid columns={2} bg='gray.700' w='100%' rounded="lg"
    align="center" p={6}>
      <Heading alignSelf='center' size="lg" color="teal.400">{props.project.title}</Heading>

    <Flex 
      w='100%' 
      rounded="lg"
      align="center"
      justifyContent="space-between"
      px={3}
    >
      <Stack direction='row' justify={"right"} spacing={6}>
        <CardLabel value= {props.project.role} name='Your Role'/>
        <CardLabel value= {props.project.lastEdit} name='Last edited'/>

      </Stack>
      <Button bg="teal.400" _hover={{ bg: "teal.600" }} w={20} ml={5}>Open</Button>

    </Flex>
    </SimpleGrid>
  );
}

export default Card;
