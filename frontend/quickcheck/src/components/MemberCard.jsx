import React from 'react';
import {
  Button,
  Table,
  Thead,
  Th,
  Tr,
  Select,
  Center,
  Wrap,
  Td,
  Tfoot,
  Tbody,
  Box,
  Spacer,
  Text,
  Stack,
  useDisclosure,
  Flex,
  Modal,
  ModalOverlay,
  ModalContent,
  ModalFooter,
  ModalCloseButton,
  FormControl,
  FormLabel,
  Input,
  ModalBody,
  ModalHeader,
  IconButton,
  Popover,
  PopoverTrigger,
  Heading,
  PopoverContent,
  PopoverHeader,
  PopoverBody,
  PopoverFooter,
} from '@chakra-ui/react';
import Card, { MinimalCard } from './Card.jsx';
import ShowEditable, { ContentSwitch } from '../components/editable.jsx';
import { DeleteIcon, AddIcon } from '@chakra-ui/icons';
import { useState } from 'react';

function AddButton({ editable, addMemberFunc }) {
  const { isOpen, onOpen, onClose } = useDisclosure();
  const [email, setEmail] = useState('');
  const [role, setRole] = useState('Client');
  if (editable) {
    return (
      <>
        <IconButton icon={<AddIcon />} bg="purple.400" p={1} w={16} onClick={onOpen} />

        <Modal isOpen={isOpen} onClose={onClose}>
          <ModalOverlay />
          <ModalContent>
            <ModalHeader>Add new Member</ModalHeader>
            <ModalCloseButton />
            <ModalBody pb={6}>
              <FormControl>
                <FormLabel pl={3}>Email</FormLabel>
                <Input placeholder="Email" onChange={(e) => setEmail(e.target.value)} />
              </FormControl>
            </ModalBody>
            <ModalBody pb={6}>
              <Select onChange={(e) => setRole(e.target.value)}>
                <option value="Client">Client</option>
                <option value="Project Manager">Project Manager</option>
                <option value="Project Owner">Project Owner</option>
              </Select>
            </ModalBody>

            <ModalFooter>
              <Button
                colorScheme="blue"
                mr={3}
                onClick={(e) => {
                  addMemberFunc({ email: email, role: role });
                  onClose();
                }}
              >
                Save
              </Button>
              <Button onClick={onClose}>Cancel</Button>
            </ModalFooter>
          </ModalContent>
        </Modal>
      </>
    );
  } else {
    return <></>;
  }
}

function RemoveButton({ removeFunc }) {
  const { onOpen, onClose, isOpen } = useDisclosure();
  return (
    <Popover isOpen={isOpen} onOpen={onOpen} onClose={onClose} isLazy={true} w="wrap">
      <PopoverTrigger>
        <IconButton
          icon={<DeleteIcon />}
          onClick={onOpen}
          size="sm"
          color="red.900"
          bg="red.400"
          p={3}
        />
      </PopoverTrigger>
      <PopoverContent>
        <PopoverHeader fontWeight="semibold">Confirm Removal</PopoverHeader>
        <PopoverBody>
          <Text>Really remove this User?</Text>
        </PopoverBody>
        <PopoverFooter>
          <Button
            colorScheme="red"
            mx={1}
            onClick={(e) => {
              removeFunc();
              onClose();
            }}
          >
            Remove
          </Button>
          <Button mx={1} onClick={onClose}>
            Cancel
          </Button>
        </PopoverFooter>
      </PopoverContent>
    </Popover>
  );
}

function MemberRow2({ editable, member, removeFunc, updateRole }) {
  return (
    <Wrap p={1} px={4} rounded="md" _hover={{ bg: 'gray.600' }} align="center" spacing={2}>
      <Text w="40%" bg="blue.700" rounded="md" p={1} px={3} h="full" align="left">
        {member.email}
      </Text>

      {editable ? (
        <Text w="40%" align="left">
          {member.role}
        </Text>
      ) : (
        <Text w="40%" align="left">
          {member.role}
        </Text>
      )}
      {editable ? <RemoveButton removeFunc={() => removeFunc(member)} /> : <div />}
    </Wrap>
  );
}

function MemberRow({ editable, member, removeFunc, updateRole }) {
  return (
    <Tr>
      <Td>
        <Box rounded={'md'} w="200px" bg="blue.500" p={2}>
          <Text color={'gray.100'} fontWeight={500} fontSize="md">
            <ShowEditable text={member.email} editable={editable}></ShowEditable>
          </Text>
        </Box>
      </Td>

      <Td>
        <Box rounded={'md'} w="200px" bg="blue.500" p={2}>
          <Text color={'gray.100'} fontWeight={500} fontSize="md">
            <ShowEditable text={member.role} editable={editable}></ShowEditable>
          </Text>
        </Box>
      </Td>

      {editable ? <RemoveButton removeFunc={() => removeFunc(member)} /> : <div />}
      <Button onClick={(e) => updateRole(member, 'Project Manager')}>as manager</Button>
    </Tr>
  );
}

// Assumption: ProjectMembers is a list of object: {id, role}
export default function MemberCard({ editable, members, memberUpdater }) {
  const removeMember = (member) => {
    const newMembers = members.filter((m) => {
      return m.email !== member.email;
    });
    memberUpdater(newMembers);
  };

  const addMember = (newMember) => {
    memberUpdater([...members, newMember]);
  };

  const updateRole = (member, newRole) => {
    // the state is updated, however it is somehow not rendered

    let index = members.findIndex((m) => m.email === member.email);

    console.log('index', index);
    members[index] = { ...member, role: newRole };
    memberUpdater(members);
  };

  return (
    <Card barColor="teal.500">
      <Stack>
        <Flex justifyContent="center">
          <Heading size="lg" mx={16}>
            Members
          </Heading>
          <AddButton editable={editable} addMemberFunc={addMember}></AddButton>
        </Flex>
        <Table variant="simple" size="sm">
          <Thead>
            <Tr>
              <Th w="45%">Email</Th>
              <Th w="45%">Role</Th>
              <Th w="10%" isNumeric></Th>
            </Tr>
          </Thead>
          <Tbody>
            {members.map((member) => (
              <MemberRow
                key={member.email}
                member={member}
                editable={editable}
                removeFunc={removeMember}
                updateRole={updateRole}
              ></MemberRow>
            ))}
          </Tbody>
          <Tfoot></Tfoot>
        </Table>
        `
        {members.map((member) => (
          <MemberRow2
            key={member.email}
            member={member}
            editable={editable}
            removeFunc={removeMember}
            updateRole={updateRole}
          ></MemberRow2>
        ))}
        `
      </Stack>
    </Card>
  );
}
