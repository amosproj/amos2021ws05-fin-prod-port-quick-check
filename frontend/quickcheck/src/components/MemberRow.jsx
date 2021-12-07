
import React from 'react';
import {
  HStack,
  Text,
  useColorModeValue,
} from '@chakra-ui/react';

//components
import { roles } from '../utils/const';
import { Selection } from './Inputs.jsx';


export default function MemberRow({ editMode, member, onChangeRole, removeButton }) {
  const bg = useColorModeValue('gray.200', 'gray.600');

  return (
    <HStack px={4} rounded="md" align="center" spacing={3}>
      <Text variant="cell" w="50%" align="left">
        {member.email}
      </Text>
      {editMode ? (
        <Selection
          bg={bg}
          border="0px"
          selected={member.role}
          options={Object.values(roles)}
          onChange={onChangeRole}
          w={48}
        />
      ) : (
        <Text w={48} variant="cell" h="full" align="left">
          {member.role}
        </Text>
      )}
      {editMode ? removeButton : <div />}
    </HStack>
  );
}
