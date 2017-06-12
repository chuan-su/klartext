require 'excon'

module Klartext; end

module Klartext::XmlStream
  def parse_stream(url,start_token,stop_token,&block)
    partial=nil
    state = :scan_start

    streamer = lambda do |chunk,remaining,total|
      start_idx = 0
      stop_idx = 0
      chunk_complete=false

      until chunk_complete
        #puts "State #{state}"
        case state
        when :scan_start
          start_idx = chunk.index start_token, stop_idx
          state = start_idx.nil? ? :next : :scan_stop
        when :scan_stop
          stop_idx = chunk.index stop_token , start_idx
          state = stop_idx.nil? ? :store_partial : :yield
        when :yield
          if partial.nil?
            content= chunk[Range.new(start_idx,stop_idx + stop_token.length - 1)]
            block.call content
          else
            content = partial + chunk[Range.new(0,stop_idx + stop_token.length - 1)]
            block.call content
            partial=nil
          end
          state = :scan_start
        when :store_partial
          partial = chunk[start_idx..-1]
          state = :scan_stop
          chunk_complete=true
        when :next
          state = :scan_start
          chunk_complete=true
        end
      end
    end
    Excon.get(url,response_block: streamer)
  end
end
